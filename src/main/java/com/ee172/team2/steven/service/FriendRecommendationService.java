package com.ee172.team2.steven.service;

import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class FriendRecommendationService {

    public static class Person {
        List<String> interests;
        int age;
        int height;

        public Person(List<String> interests, int age, int height) {
            this.interests = interests;
            this.age = age;
            this.height = height;
        }
    }

    public static class SimilarityScore {
        double score;
        Person person;

        public SimilarityScore(double score, Person person) {
            this.score = score;
            this.person = person;
        }
    }

    public double calculateSimilarity(Person person1, Person person2) {
        double score = 0.0;

        int commonInterests = 0;
        for (String interest1 : person1.interests) {
            if (person2.interests.contains(interest1)) {
                commonInterests++;
            }
        }
        score += commonInterests * 0.6;

        int ageDifference = Math.abs(person1.age - person2.age);
        score += (1.0 / (1 + ageDifference)) * 0.3;

        if (person1.height >= 165 && person1.height <= 190 &&
                person2.height >= 165 && person2.height <= 190) {
            int heightScore = Math.min(person1.height, person2.height) - 164;
            score += heightScore * 0.1;
        }

        return score;
    }

    public List<Person> findTop10SimilarPersons(Person targetPerson, List<Person> allPersons) {
        List<SimilarityScore> scores = new ArrayList<>();

        for (Person person : allPersons) {
            if (person.equals(targetPerson)) {
                continue;
            }
            double score = calculateSimilarity(targetPerson, person);
            scores.add(new SimilarityScore(score, person));
        }

        scores.sort((a, b) -> Double.compare(b.score, a.score));

        List<Person> top10 = new ArrayList<>();
        for (int i = 0; i < Math.min(10, scores.size()); i++) {
            top10.add(scores.get(i).person);
        }

        return top10;
    }
}

