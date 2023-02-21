package com.ntt.repository.projectspringboot.controller;

import com.ntt.repository.projectspringboot.domain.Course;
import com.ntt.repository.projectspringboot.repository.CourseRepository;
import org.hibernate.MappingException;
import org.hibernate.jdbc.Expectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.ExportException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/course")
@RestController
public class CourseController {
    @Autowired
    CourseRepository courseRepository;
    @GetMapping("")
    public ResponseEntity<List<Course>> returnCourse(){

        List<Course> listaCourse = courseRepository.findAll();

        return new ResponseEntity<>(listaCourse, HttpStatus.OK);
    }


    //Slide 5
    @GetMapping("/endopoint_numeri")
    public ResponseEntity<Integer> returnEndpointNumeri(){

        List<Course> listaCourse = courseRepository.findAll();

        return new ResponseEntity<>(listaCourse.size(), HttpStatus.OK);
    }



    //Slide 7
    @GetMapping("/endopoint_descrizione")
    public ResponseEntity<List<Course>> returnEndpointDescrizione(){


        List<Course> course_list = courseRepository.findAll();
        for (Course courseIter : course_list) {
            if (courseIter.getNome().length() < 20) {
                return new ResponseEntity<>(course_list, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





    /*
    @GetMapping()
    public ResponseEntity<Map<Long, String>> returnCourse(){
        Map<Long, String> mappa = new HashMap<>();
        Course course = new Course(1L, "Analisi1", "Robe di matematica");

        try{

            mappa.put(course.getId(), course.getNome());
            //La mappa non può possedere chiavi uguali, quindi lo fa da solo
            throw new MappingException("error:duplicate");
        }catch(Exception e){
            e.getMessage();
        }
        return new ResponseEntity<>(mappa, HttpStatus.OK);
    }*/

    //Esercizio slide 3
    @PostMapping("/prefixcontrollo")
    public ResponseEntity<?> createCoursePrefix(@RequestBody Course course) {
        if(course.getNome().substring(0, 6).equals("corso_") && course.getNome().length() > 7){
            Course course1 = courseRepository.save(course);
            return new ResponseEntity<>(course1, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Le condizioni non sono rispettate", HttpStatus.NO_CONTENT);

    }

    @PostMapping()
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        Course course1 = courseRepository.save(course);

        return new ResponseEntity<>(course1, HttpStatus.CREATED);
    }

    //controllo se c'è un doppione
    @PutMapping("{id}")
    public ResponseEntity<?> updateCourse(@PathVariable("id") long id, @RequestBody Course course){


        List<Course> course_list = courseRepository.findAll();
        for (Course courseIter: course_list){
            if(courseIter.getId()== course.getId()){
                System.out.println("Ci sono id duplicati");
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }

        }

        courseRepository.save(course);
        return new ResponseEntity<>(course, HttpStatus.OK);


    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") long id) {
        courseRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
