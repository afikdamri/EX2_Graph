import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Vector;

public class deserialize implements JsonDeserializer {
    @Override
    public myDirectedWeightedGraph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        myDirectedWeightedGraph theGraph = new myDirectedWeightedGraph();
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonArray jNodeArr = jsonObject.getAsJsonArray("Nodes");
        for (int i = 0; i < jNodeArr.size(); i++) {
            JsonObject jasonObj = (JsonObject) jNodeArr.get(i);
            myGeoLocation loc = new myGeoLocation(jasonObj.get("pos").getAsString());
            myNodeData me = new myNodeData(jasonObj.get("id").getAsInt(), loc);
            theGraph.getNodes().put(me.getKey(), me);
        }

        JsonArray jEdgeArr = jsonObject.getAsJsonArray("Edges");
        for (int i = 0; i < jEdgeArr.size(); i++) {
            JsonObject jasonObj = (JsonObject) jEdgeArr.get(i);
            myEdgeData me = new myEdgeData(jasonObj.get("src").getAsInt(), jasonObj.get("dest").getAsInt(), jasonObj.get("w").getAsDouble());
            theGraph.getEdges().put(new Vector(me.src,me.dest),me);
        }
        theGraph.getEdgesOfNode();

        return theGraph;
    }
}
