import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


/*

Format class for input in http://geojson.io

{
  "type": "FeatureCollection",
  "features": [
    {
      "type": "Feature",
      "geometry": {
        "type": "Point",
        "coordinates": [
          24.42091394, (longitude)
          57.04677303 (latitude)
        ]
      },
      "properties": {}
    },
    ...
  ]
}
 */
public class FeatureCollection {

    private String type;
    private ArrayNode featuresArrayNode;
    private ObjectNode collection;
    private ObjectNode properties;

    FeatureCollection() {
        ObjectMapper mapper = new ObjectMapper();
        collection = mapper.createObjectNode();
        this.type = "FeatureCollection";
        this.featuresArrayNode = mapper.createArrayNode();
        collection.put("type", this.type);
        collection.set("features", this.featuresArrayNode);

        //custom marker properties
        properties = mapper.createObjectNode();
        properties.put("marker-color", "#ff0000");
        properties.put("marker-size", "medium");
        properties.put("marker-symbol", "");
        properties.put("type", "landmark");
        properties.put("name", "name");

    }

    public void addFeature(ObjectNode feature){
        if(feature != null){
            this.featuresArrayNode.add(feature);
        }
    }

    public ObjectNode createFeature(ObjectNode geometry){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode feature = mapper.createObjectNode();
        feature.put("type", "Feature");
        feature.set("geometry", geometry);
        feature.set("properties", this.properties);

        return feature;
    }

    public ObjectNode createGeometry(ArrayNode coordinates){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode geometry = mapper.createObjectNode();
        geometry.put("type", "Point");
        geometry.set("coordinates", coordinates);
        return geometry;
    }

    public ObjectNode getCollection() {
        return collection;
    }

}