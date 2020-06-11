import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FeatureCollectionTest {

    private FeatureCollection featureCollection;
    private ObjectMapper mapper;
    private ArrayNode arrayNode;

    private final static double longitude = 24.42086410522461;
    private final static double latitude = 57.04664993286133;

    @Before
    public void before(){
        featureCollection = new FeatureCollection();
        mapper = new ObjectMapper();
        arrayNode = mapper.createArrayNode();
    }

    @Test
    public void initCollection_positive() {

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("type", "FeatureCollection");
        objectNode.set("features", arrayNode);

        assertNotNull(featureCollection);
        assertEquals(objectNode, featureCollection.getCollection());

    }

    @Test
    public void createGeometry_positive() {

        ArrayNode coordinateArrayNode = arrayNode;

        coordinateArrayNode.add(longitude);
        coordinateArrayNode.add(latitude);
        ObjectNode featureCollectionGeometry = featureCollection.createGeometry(coordinateArrayNode);

        ObjectNode geometry = mapper.createObjectNode();
        geometry.put("type", "Point");
        geometry.set("coordinates", coordinateArrayNode);

        assertEquals(geometry, featureCollectionGeometry);

    }

    @Test
    public void createFeature_positive() {

        ArrayNode coordinateArrayNode = arrayNode;

        coordinateArrayNode.add(longitude);
        coordinateArrayNode.add(latitude);
        ObjectNode featureCollectionGeometry = featureCollection.createGeometry(coordinateArrayNode);
        ObjectNode collectionFeature = featureCollection.createFeature(featureCollectionGeometry);

        ObjectNode feature = mapper.createObjectNode();
        feature.put("type", "Feature");

        ObjectNode geometry = mapper.createObjectNode();
        geometry.put("type", "Point");
        geometry.set("coordinates", coordinateArrayNode);
        feature.set("geometry", geometry);

        ObjectNode properties = mapper.createObjectNode();
        properties.put("marker-color", "#ff0000");
        properties.put("marker-size", "medium");
        properties.put("marker-symbol", "");
        properties.put("type", "landmark");
        properties.put("name", "name");
        feature.set("properties", properties);

        assertEquals(feature, collectionFeature);

    }

    @Test
    public void addFeature_positive() {

        ArrayNode coordinateArrayNode = arrayNode;

        coordinateArrayNode.add(longitude);
        coordinateArrayNode.add(latitude);
        ObjectNode featureCollectionGeometry = featureCollection.createGeometry(coordinateArrayNode);
        ObjectNode collectionFeature = featureCollection.createFeature(featureCollectionGeometry);
        featureCollection.addFeature(collectionFeature);


        ObjectNode geometry = mapper.createObjectNode();
        geometry.put("type", "Point");
        geometry.set("coordinates", coordinateArrayNode);

        ObjectNode properties = mapper.createObjectNode();
        properties.put("marker-color", "#ff0000");
        properties.put("marker-size", "medium");
        properties.put("marker-symbol", "");
        properties.put("type", "landmark");
        properties.put("name", "name");

        ObjectNode feature = mapper.createObjectNode();
        feature.put("type", "Feature");
        feature.set("properties", properties);
        feature.set("geometry", geometry);

        ArrayNode features = mapper.createArrayNode();
        features.add(feature);

        ObjectNode collection = mapper.createObjectNode();
        collection.put("type", "FeatureCollection");
        collection.set("features", features);

        assertEquals(collection, featureCollection.getCollection());

    }

}


