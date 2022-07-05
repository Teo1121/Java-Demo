package main;

public class Student {
    private String jmbag;
    private String name;
    private String surname;
    private Integer grade;

    public Student(String jmbag, String name, String surname, int grade) {
        this.jmbag = jmbag;
        this.name = name;
        this.surname = surname;
        this.grade = grade;
    }
    public Student(String[] csvLine) {
        this.jmbag = csvLine[0];
        this.name = csvLine[1];
        this.surname = csvLine[2];
        this.grade = (Integer.parseInt(csvLine[3]));
    }

    public String getJmbag() {
        return jmbag;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getFullName() {
        return this.name + " " + this.surname;
    }

    @Override
    public String toString() {
        return "Student{" +
                "jmbag='" + jmbag + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", grade=" + grade +
                '}';
    }
}
