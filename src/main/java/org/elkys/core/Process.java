package org.elkys.core;

import java.util.Iterator;
import java.util.Map;
import org.elkys.model.Model.TemplateObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

/**
 * 
 * @author Carlos Yépez
 *
 */
public class Process {
	
	/**
	 * Recibe el contenido de dos JSON, y recursivamente genera una
	 * salida con los valores correspondientes.
	 * @param jsonTemplate Archvo JSON con la plantilla
	 * @param jsonPayload Archivo JSON con la data
	 * @return String con el valor del resultado generado
	 * @throws Exception
	 */
	public static String modifyJson(String jsonTemplate, String jsonPayload) throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
    	JsonNode jsonNodeT = mapper.readTree(jsonTemplate);
    	JsonNode jsonNodeP = mapper.readTree(jsonPayload);

    	return modifyNode(jsonNodeT, jsonNodeP).toString();
    }

	/**
	 * A partir de un nodo, y de la entrada de datos datos original,
	 * recorre recusivamente cada elemento del nodo para calcular los
	 * valores correspondientes
	 * @param nodeT Nodo a evaular
	 * @param nodeP Data de entrada
	 * @return Nodo resultado
	 * @throws Exception
	 */
    private static JsonNode modifyNode(JsonNode nodeT, JsonNode nodeP) throws Exception {
    	
    	//Si es un objeto complejo
    	if (nodeT.isObject()) {
    		ObjectNode objectNode = (ObjectNode) nodeT.deepCopy();
    		Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
    		
    		while (fields.hasNext()) {
    			Map.Entry<String, JsonNode> entry = fields.next();

    			String key = entry.getKey();
    			JsonNode value = entry.getValue();
    			
    			if (value.has("_value")) {
    				
    				TemplateObject t = (new ObjectMapper()).readValue(value.toString(), TemplateObject.class);
    				
    				Object jsonValue = JsonPath.read(nodeP.toString(), t.getValue());
    				if (jsonValue  instanceof JSONArray) {
    					
    					if (((JSONArray)jsonValue).size() == 0) {
    						ObjectMapper objectMapper = new ObjectMapper();
    			            value = objectMapper.valueToTree(null);
    			            objectNode.set(key, modifyNode(value, nodeP));
    						continue;
    					}
    					
    					
    					Object objValue = ((JSONArray)jsonValue).get(0);
    					if (objValue != null) {
    						ObjectMapper objectMapper = new ObjectMapper();
    			            value = objectMapper.valueToTree(objValue);
    					} else if (t.getDefaultValue() == null && t.getRequired()) {
    						throw new Exception("error.valor_template_requerido");
    					} else  if(t.getDefaultValue() != null){
    						ObjectMapper objectMapper = new ObjectMapper();
    			            value = objectMapper.valueToTree(t.getDefaultValue());
    					} else {
    						ObjectMapper objectMapper = new ObjectMapper();
    			            value = objectMapper.valueToTree(null);
    					}
    					
    		            objectNode.set(key, modifyNode(value, nodeP));
    					
    				}
    			} else {
    				objectNode.set(key, modifyNode(value, nodeP));
    			}
    			
    		}
    		return objectNode;
    		
    	//Si es un arreglo
    	} else if (nodeT.isArray()) {
    		ArrayNode an = (new ObjectMapper()).createArrayNode();
    		for (int i = 0; i < ((ArrayNode)nodeT).size(); i++) {
                JsonNode jsonNode = ((ArrayNode)nodeT).get(i);
                an.addPOJO(Process.modifyNode(jsonNode, nodeP));
    		}
    		return an;
    	// Si es un objeto simple/primitivo
    	} else {
    		return nodeT;
    	}
    }

}
