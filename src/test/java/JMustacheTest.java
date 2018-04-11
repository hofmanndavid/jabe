import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.util.*;

public class JMustacheTest {

    public static void main( String[] args ) {

//        first();
//        second();
        third();
    }

    static void first() {
        String text = "One, two, {{three}}. Three sir!";
        Template tmpl = Mustache.compiler().compile(text);
        Map<String, String> data = new HashMap<>();
        data.put("three", "five");
        System.out.println(tmpl.execute(data));
    }


    static class ComplexDTO {
        List persons = new ArrayList() {{
            add(new Person("Elvis", 75));
            add(new Person("Madonna", 52));
        }};
        String message = "lekisima";
    }
    static void third() {

        ComplexDTO complexDTO = new ComplexDTO();

        String tmpl =
                "{{#persons}} \n" +
                        "{{name}}: {{age}} - {{date}} \n " +
                        "{{/persons}}\n"+
                        "Surprese here:! {{message}}";
        String rendered = Mustache.compiler().compile(tmpl).execute(complexDTO);

//        String tmpl =
//                "{{#cdto.persons}} \n" +
//                        "{{name}}: {{age}} - {{date}} \n " +
//                        "{{/cdto.persons}}\n"+
//                        "Surprese here:! {{cdto.message}}";
//        String rendered = Mustache.compiler().compile(tmpl).execute(new Object() {
//            Object cdto = complexDTO;
//        });

        System.out.println(rendered);

    }
    static void second() {

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Elvis", 75));
        persons.add(new Person("Madonna", 52));



        String tmpl =
                "{{#plist}} " +
                        "{{name}}: {{age}} - {{date}} \n " +
                        "{{/plist}}\n";
        String rendered = Mustache.compiler().compile(tmpl).execute(new Object() {
            Object plist = persons;
        });
        System.out.println(rendered);

    }

    static class Person {
        public final String name;
        public Person (String name, int age) {
            this.name = name;
            _age = age;
        }
        public String date() {
            return new Date()+"";
        }
        public int getAge () {
            return _age;
        }
        protected int _age;
    }
}
