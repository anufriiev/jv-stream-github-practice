package practice;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import model.Candidate;
import model.Cat;
import model.People;

public class StreamPractice {

    public int findMinEvenNumber(List<String> numbers) {
        List<String[]> collect = numbers.stream()
                .map(s -> s.split(","))
                .collect(Collectors.toList());
        return collect.stream()
                .flatMap(Arrays::stream)
                .mapToInt(Integer::parseInt)
                .filter(n -> n % 2 == 0)
                .min()
                .orElseThrow(() ->
                        new RuntimeException("Can't get min value from list: "
                                + numbers));
    }

    public Double getOddNumsAverage(List<Integer> numbers) {

        return IntStream.range(0, numbers.size())
                .mapToDouble(i -> i % 2 != 0 ? numbers.get(i) - 1 : numbers.get(i))
                .filter(i -> i % 2 != 0)
                .average()
                .orElseThrow(NoSuchElementException::new);
    }

    public List<People> selectMenByAge(List<People> peopleList, int fromAge, int toAge) {
        return peopleList.stream()
                .filter(p -> p.getAge() >= fromAge
                        && p.getAge() <= toAge
                        && p.getSex().equals(People.Sex.MAN))
                .collect(Collectors.toList());
    }

    public List<People> getWorkablePeople(int fromAge, int femaleToAge,
                                          int maleToAge, List<People> peopleList) {
        return peopleList.stream()
                 .filter(p -> p.getAge() >= fromAge
                        && (p.getSex().equals(People.Sex.MAN)
                         ? p.getAge() <= maleToAge :
                        p.getAge() <= femaleToAge))
                .collect(Collectors.toList());
    }

    public List<String> getCatsNames(List<People> peopleList, int femaleAge) {
        return peopleList.stream()
                .filter(owner -> owner.getSex().equals(People.Sex.WOMEN)
                && owner.getAge() >= femaleAge)
                .map(People::getCats)
                .flatMap(Collection::stream)
                .map(Cat::getName)
                .collect(Collectors.toList());
    }

    public static List<String> validateCandidates(List<Candidate> candidates) {
        return candidates.stream()
                .filter(new CandidateValidator())
                .map(Candidate::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
