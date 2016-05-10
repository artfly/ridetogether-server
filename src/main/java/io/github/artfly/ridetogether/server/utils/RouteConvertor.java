package io.github.artfly.ridetogether.server.utils;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import io.github.artfly.ridetogether.server.repository.entities.Coordinate;
import io.github.artfly.ridetogether.server.repository.entities.Route;
import io.github.artfly.ridetogether.server.repository.entities.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class RouteConvertor {
//    public static class Serializer extends JsonSerializer<Route> {
//        @Override
//        public void serialize(Route route, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//            Map<String, Object> geometry = new HashMap<>();
//            Map<String, Object> properties = new HashMap<>();
//            Map<String, Object> routeJson = new LinkedHashMap<>();
//            List<List<BigDecimal>> coordinates = new ArrayList<>();
//
//            properties.put("id", route.getId());
//            properties.put("title", route.getTitle());
//            properties.put("description", route.getDescription());
//            properties.put("rating", route.getRating());
//            properties.put("added_at", route.getAddedAt());
//            properties.put("creator_id", route.getCreator().getId());
//            properties.put("route_type", route.getRouteType());
//
//            geometry.put("type", "LineString");
//            route.getCoordinates().forEach(
//                    coordinate -> coordinates.add(
//                            Arrays.asList(coordinate.getLatitude(), coordinate.getLatitude()))
//            );
//            geometry.put("coordinates", coordinates);
//
//            routeJson.put("type", "Feature");
//            routeJson.put("properties", properties);
//            routeJson.put("geometry", geometry);
//
//            jsonGenerator.writeObject(routeJson);
//        }
//    }
//
//    public static class Deserializer extends JsonDeserializer<Route> {
//        @Override
//        public Route deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//            Route route = new Route();
//            JsonNode routeNode = jsonParser.getCodec().readTree(jsonParser);
//            //in case if it's only id of route
//            if (!routeNode.has("properties")) {
//                route.setId(routeNode.asLong());
//                return route;
//            }
//            JsonNode properties = routeNode.get("properties");
//            JsonNode geometry = routeNode.get("geometry");
//            JsonNode coordinates = geometry.get("coordinates");
//
//            Long creatorId = properties.get("creator_id").asLong();
//
//            route.setCreator(new User(creatorId));
//            route.setTitle(properties.get("title").asText());
//            route.setDescription(asNullOrText(properties.get("description")));
//            route.setRouteType(asNullOrText(properties.get("route_type")));
//            route.setRating(asNullOrDouble(properties.get("rating")));
//
//            coordinates.forEach(coordinate ->
//                    route.addCoordinate(new Coordinate(asNullOrText(coordinate.get(0)), asNullOrText(coordinate.get(1))))
//            );
//            return route;
//        }
//
//        private static String asNullOrText(JsonNode node) {
//            return node.isNull() ? null : node.asText();
//        }
//
//        private static Double asNullOrDouble(JsonNode node) {
//            return node.isNull() ? null : node.asDouble();
//        }
//    }
}
