package com.example.farmersapp.model;

public class DiseasesModel {



     String diseaseBiologicalControl;
     String diseaseBrief;
      String diseaseCause;
     String diseaseChemicalControl;
     String diseaseScientificName;
     String diseaseTitle;
     String diseaseType;

    public DiseasesModel() {
    }

    public DiseasesModel(String diseaseBiologicalControl, String diseaseBrief, String diseaseCause, String diseaseChemicalControl, String diseaseScientificName, String diseaseTitle, String diseaseType) {
        this.diseaseBiologicalControl = diseaseBiologicalControl;
        this.diseaseBrief = diseaseBrief;
        this.diseaseCause = diseaseCause;
        this.diseaseChemicalControl = diseaseChemicalControl;
        this.diseaseScientificName = diseaseScientificName;
        this.diseaseTitle = diseaseTitle;
        this.diseaseType = diseaseType;
    }

    public String getDiseaseBiologicalControl() {
        return diseaseBiologicalControl;
    }

    public String getDiseaseBrief() {
        return diseaseBrief;
    }

    public String getDiseaseCause() {
        return diseaseCause;
    }

    public String getDiseaseChemicalControl() {
        return diseaseChemicalControl;
    }

    public String getDiseaseScientificName() {
        return diseaseScientificName;
    }

    public String getDiseaseTitle() {
        return diseaseTitle;
    }

    public String getDiseaseType() {
        return diseaseType;
    }
}
