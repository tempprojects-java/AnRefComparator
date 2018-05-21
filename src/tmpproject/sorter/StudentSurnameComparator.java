package tmpproject.sorter;

import com.gmail.gak.artem.Student;

import java.util.Comparator;

@Active
public class StudentSurnameComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getSurname().compareTo(o2.getSurname());
    }
}
