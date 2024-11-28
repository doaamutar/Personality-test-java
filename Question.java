class Question {
    private int id;
    private String text;
    private int answer;
    private PersonalityDimension dimension;
    
    public Question(int id, String text, PersonalityDimension dimension) {
        this.id = id;
        this.text = text;
        this.dimension = dimension;
        this.answer = 0;
    }
    
    // Getters and setters
    public int getId() { return id; }
    public String getText() { return text; }
    public int getAnswer() { return answer; }
    public void setAnswer(int answer) { this.answer = answer; }
    public PersonalityDimension getDimension() { return dimension; }
}