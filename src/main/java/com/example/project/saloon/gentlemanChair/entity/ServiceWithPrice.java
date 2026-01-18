package com.example.project.saloon.gentlemanChair.entity;

import java.util.HashMap;
import java.util.Map;

public class ServiceWithPrice {
    public static Map<String, Map<String, Integer>> getServiceAndPrices() {

        Map<String, Map<String, Integer>> finalMap = new HashMap<>();

        Map<String, Integer> hairStyle = new HashMap<>();
        hairStyle.put("Haircut", 200);
        hairStyle.put("Hair Styling", 150);
        hairStyle.put("Hair Wash", 100);
        hairStyle.put("Hair Treatment", 500);
        hairStyle.put("Hair Coloring", 800);
        finalMap.put("HairStyle", hairStyle);

        Map<String, Integer> beardStyle = new HashMap<>();
        beardStyle.put("Beard Trim", 150);
        beardStyle.put("Beard Styling", 200);
        beardStyle.put("Beard Coloring", 400);
        beardStyle.put("Clean Shave", 180);
        beardStyle.put("Eyebrow Trim", 80);
        beardStyle.put("Mustache Trim", 70);
        finalMap.put("BeardStyle", beardStyle);

        Map<String, Integer> skinCare = new HashMap<>();
        skinCare.put("Facial", 600);
        skinCare.put("De-tan", 700);
        skinCare.put("Face Cleanup", 400);
        skinCare.put("Neck & Ear Cleanup", 60);
        finalMap.put("Skin Care", skinCare);

        Map<String, Integer> massage = new HashMap<>();
        massage.put("Head Massage", 300);
        massage.put("Shoulder Massage", 250);
        massage.put("Full Body Massage", 1200);
        finalMap.put("Massage & Relaxation", massage);

        return finalMap;
    }
}
