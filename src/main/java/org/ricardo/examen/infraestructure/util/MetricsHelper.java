package org.ricardo.examen.infraestructure.util;

import lombok.experimental.UtilityClass;
import org.ricardo.examen.domain.model.Client;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public class MetricsHelper {

    public static double calculateAverage(List<Integer> ages) {
        if (ages == null || ages.isEmpty()) {
            return 0; // Manejo de caso vacío
        }

        int sum = 0;
        for (int age : ages) {
            sum += age;
        }
        return (double) sum / ages.size();
    }

    public static double calculateStandardDeviation(List<Integer> ages, double average) {
        if (ages == null || ages.isEmpty()) {
            return 0; // Manejo de caso vacío
        }

        double sumaCuadrados = 0;
        for (int age : ages) {
            sumaCuadrados += Math.pow(age - average, 2);
        }
        return Math.sqrt(sumaCuadrados / ages.size());
    }

    public static List<Integer> getAges(List<Client> clients){
        return clients.stream().map(Client::getAge).toList();
    }

    public static Map<Integer, Double> calculateSurvivalRates(List<Integer> ages) {
        // Contar frecuencia de cada edad
        Map<Integer, Long> ageFrequencies = ages.stream()
                .collect(Collectors.groupingBy(age -> age, Collectors.counting()));

        // Calcular la frecuencia acumulada inversa para las tasas de supervivencia
        long totalPopulation = ages.size();

        return ageFrequencies.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            int age = entry.getKey(); // Ahora se puede resolver correctamente
                            long survivors = ageFrequencies.entrySet().stream()
                                    .filter(e -> e.getKey() >= age)
                                    .mapToLong(Map.Entry::getValue)
                                    .sum();
                            return (double) survivors / totalPopulation;
                        },
                        (a, b) -> a, // Merge function (no aplica en este caso)
                        LinkedHashMap::new // Asegurar orden de inserción
                ));
    }

    public static double calculateLifeExpectancy(Map<Integer, Double> survivalRates) {
        double expectancy = 0.0;

        // Recorrer las edades ordenadas
        Integer[] ages = survivalRates.keySet().toArray(new Integer[0]);
        Arrays.sort(ages);

        // Calcular esperanza de vida como suma ponderada de años vividos
        for (int i = 0; i < ages.length - 1; i++) {
            int age = ages[i];
            int nextAge = ages[i + 1];
            double survivalRate = survivalRates.get(age);

            // Añadir los años ponderados por la probabilidad de supervivencia
            expectancy += (nextAge - age) * survivalRate;
        }

        // Añadir los años restantes ponderados
        int lastAge = ages[ages.length - 1];
        expectancy += (100 - lastAge) * survivalRates.get(lastAge);

        return expectancy;
    }

}
