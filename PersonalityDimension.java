public enum PersonalityDimension {
    EXTROVERSION("Extroversion"),
    AGREEABLENESS("Agreeableness"),
    CONSCIENTIOUSNESS("Conscientiousness"),
    NEUROTICISM("Neuroticism"),
    OPENNESS("Openness");
    
    private String name;
    PersonalityDimension(String name) { this.name = name; }
    public String getName() { return name; }
}