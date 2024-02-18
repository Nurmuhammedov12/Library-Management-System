package com.LMS.controller;

import com.LMS.domain.Teacher;
import com.LMS.dto.TeacherDTO;
import com.LMS.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/saveTeacher")
    public ResponseEntity<Map<String,String>> saveTeacher(@Valid @RequestBody Teacher teacher){
        teacherService.createTeacher(teacher);

        Map<String,String> message = new HashMap<>();
        message.put("message", "Created Successfully");
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
   public ResponseEntity<List<Teacher>> getallteacher(){

        return ResponseEntity.ok(teacherService.getallteachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> findById(@PathVariable("id") Long id){
        Teacher teacher = teacherService.findById(id);
        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,String>> deleteById(@PathVariable("id") Long id){
        teacherService.deleteById(id);
        Map<String,String> message = new HashMap<>();
        message.put("message", "Deleted successfully");
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,String>> updateTeacher(@PathVariable("id") Long id, @RequestBody TeacherDTO teacherDTO){
        teacherService.updateTeacher(id,teacherDTO);
        Map<String,String> message = new HashMap<>();
        message.put("message", "Updated successfully");
        return ResponseEntity.ok(message);
    }

    @GetMapping("/tch")
    public ResponseEntity<Page<Teacher>> findwTeacherswithPagination(
            @RequestParam("p") int page,
            @RequestParam("s") int size,
            @RequestParam("sort") String sort,
            @RequestParam("dir")Sort.Direction direction
            ){
        Pageable teachersPageble = PageRequest.of(page, size, Sort.by(direction,sort));
        Page<Teacher> result = teacherService.pagebleFind(teachersPageble);
        return ResponseEntity.ok(result);
    }
}
