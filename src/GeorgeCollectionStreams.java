import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class GeorgeCollectionStreams {

    public static void main(String[] args) {
        ArrayList<List<String>> objects = new ArrayList<>();
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "RA", "PAYER_COVERAGE", "commercial", "8498"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "RA", "PAYER_COVERAGE", "commercial", "8498"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "RA", "PAYER_COVERAGE", "medicare", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "RA", "PAYER_COVERAGE", "other", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "RA", "PAYER_COVERAGE", "managed", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "JAK", "PAYER_COVERAGE", "commercial", "8498"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "JAK", "PAYER_COVERAGE", "medicare", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "JAK", "PAYER_COVERAGE", "medicare", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "JAK", "PAYER_COVERAGE", "managed", "4100"));
        objects.add(List.of("TERRITORY", "ROANOKE, VA", "null", "RA", "PAYER_COVERAGE", "commercial", "8498"));
        objects.add(List.of("TERRITORY", "ROANOKE, VA", "null", "RA", "PAYER_COVERAGE", "medicare", "41007"));
        objects.add(List.of("TERRITORY", "ROANOKE, VA", "null", "RA", "PAYER_COVERAGE", "medicare", "4100"));
        objects.add(List.of("TERRITORY", "RICHMOND, VA", "null", "RA", "PAYER_COVERAGE", "medicare", "4100"));
        objects.add(List.of("TERRITORY", "RICHMOND, VA", "null", "RA", "PAYER_COVERAGE", "medicare", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "RA", "PAYER_COVERAGE", "commercial", "84938"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "RA", "PAYER_COVERAGE", "commercial", "849528"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "RA", "PAYER_COVERAGE", "medicare", "41010"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "RA", "PAYER_COVERAGE", "other", "41030"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "RA", "PAYER_COVERAGE", "managed", "453100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "JAK", "PAYER_COVERAGE", "commercial", "84398"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "JAK", "PAYER_COVERAGE", "medicare", "416200"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "JAK", "PAYER_COVERAGE", "medicare", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "null", "JAK", "PAYER_COVERAGE", "managed", "41020"));
        objects.add(List.of("TERRITORY", "ROANOKE, VA", "null", "RA", "PAYER_COVERAGE", "commercial", "846498"));
        objects.add(List.of("TERRITORY", "ROANOKE, VA", "null", "RA", "PAYER_COVERAGE", "medicare", "4100"));
        objects.add(List.of("TERRITORY", "ROANOKE, VA", "null", "RA", "PAYER_COVERAGE", "medicare", "4100"));
        objects.add(List.of("TERRITORY", "RICHMOND, VA", "null", "RA", "PAYER_COVERAGE", "medicare", "4100"));
        objects.add(List.of("TERRITORY", "RICHMOND, VA", "null", "RA", "PAYER_COVERAGE", "medicare", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "MEDICARE", "RA", "TOP_PAYERS", "CVS", "8498"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "MEDICARE", "RA", "TOP_PAYERS", "Aetna", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "COMMERCIAL", "RA", "TOP_PAYERS", "Company", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "COMMERCIAL", "RA", "TOP_PAYERS", "Other", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "COMMERCIAL", "JAK", "TOP_PAYERS", "CVS", "8498"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "COMMERCIAL", "JAK", "TOP_PAYERS", "Aetna", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "COMMERCIAL", "JAK", "TOP_PAYERS", "Company", "4100"));
        objects.add(List.of("REGION", "WASHINGTON, DC", "MEDICARE", "JAK", "TOP_PAYERS", "Other", "4100"));
        objects.add(List.of("TERRITORY", "ROANOKE, VA", "MEDICARE", "RA", "TOP_PAYERS", "CVS", "8498"));
        objects.add(List.of("TERRITORY", "ROANOKE, VA", "MEDICARE", "RA", "TOP_PAYERS", "Aetna", "4100"));
        objects.add(List.of("TERRITORY", "ROANOKE, VA", "MEDICARE", "RA", "TOP_PAYERS", "Company", "4100"));
        objects.add(List.of("TERRITORY", "RICHMOND, VA", "COMMERCIAL", "RA", "TOP_PAYERS", "Other", "4100"));
        objects.add(List.of("TERRITORY", "RICHMOND, VA", "COMMERCIAL", "JAK", "TOP_PAYERS", "CVS", "4100"));

        Map<Pair<String, String>, List<List<String>>> byChartType = objects.stream()
                .collect(groupingBy(e -> Pair.of(e.get(4), e.get(2))));
        Map<Pair<String, String>, Map<String, List<List<String>>>> byMarketType = byChartType.entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, e -> e.getValue().stream().collect(groupingBy(j -> j.get(3)))));

        List<ChartTypeData> res = byMarketType.entrySet()
                .stream()
                .map(keyValue -> {
                    ChartTypeData chartTypeData = new ChartTypeData();
                    String title;
                    title = keyValue.getKey().getRight().equalsIgnoreCase("null")
                            ? keyValue.getKey().getLeft()
                            : keyValue.getKey().getLeft() + " - " + keyValue.getKey().getRight();
                    chartTypeData.setTitle(title);
                    Map<String, List<List<String>>> value = keyValue.getValue(); //ra and jak
                    List<MarketTypeData> marketTypeData1 = value.entrySet().stream()
                            .map(market -> {
                                List<String> regionFields = market.getValue().stream()
                                        .filter(q -> q.get(0).equals("REGION")).findAny().orElseThrow();
                                MarketTypeData marketTypeData = new MarketTypeData();
                                marketTypeData.setMarketType(market.getKey());
//                                marketTypeData.setRegionId();
                                marketTypeData.setRegionName(regionFields.get(1));
                                Map<String, List<List<String>>> territories = market.getValue().stream()
                                        .filter(q -> q.get(0).equals("TERRITORY"))
                                        .collect(groupingBy(q -> q.get(1)));
                                List<TerritoryData> collect1 = territories
                                        .entrySet()
                                        .stream()
                                        .map(ter -> {
                                            TerritoryData territoryData = new TerritoryData();
//                                            territoryData.setTerritoryId();
                                            territoryData.setTerritoryName(ter.getKey());
                                            List<FieldData> collect = ter.getValue().stream().map(q -> {
                                                FieldData fieldData = new FieldData();
                                                fieldData.setPaymentType(q.get(5));
                                                fieldData.setScripts(q.get(6));
                                                return fieldData;
                                            }).collect(toList());
                                            territoryData.setData(collect);
                                            return territoryData;
                                        }).collect(toList());

                                Map<String, List<List<String>>> regions = market.getValue().stream()
                                        .collect(groupingBy(q -> q.get(5)));
                                List<FieldData> collect = market.getValue().stream().map(p -> {
                                    FieldData fieldData = new FieldData();
                                    fieldData.setPaymentType(p.get(5));
                                    fieldData.setScripts(p.get(6));
                                    return fieldData;
                                }).collect(toList());

                                marketTypeData.setData(collect);
                                marketTypeData.setTerritories(collect1);
                                return marketTypeData;
                            }).collect(toList());
                    chartTypeData.setMarketTypeData(marketTypeData1);
                    return chartTypeData;
                })
                .collect(toList());


        System.out.println(MapperUtil.writeValueAsString(new RegionalPayerDynamics(res)));
    }

}

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class RegionalPayerDynamics {
    List<ChartTypeData> data;
}

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class ChartTypeData {
    private String title;
    private List<MarketTypeData> marketTypeData;
}

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class MarketTypeData {
    private String marketType; //RA, JAK
    private String regionId;
    private String regionName;
    private List<FieldData> data;
    private List<TerritoryData> territories;
}

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class FieldData {
    private String paymentType;
    private String scripts;
}

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class TerritoryData {
    private String territoryId;
    private String territoryName;
    private List<FieldData> data;
}
