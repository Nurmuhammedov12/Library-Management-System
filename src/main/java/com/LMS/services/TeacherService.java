package com.LMS.services;

import com.LMS.domain.Teacher;
import com.LMS.dto.TeacherDTO;
import com.LMS.exception.Conflikt;
import com.LMS.exception.NotFoundException;
import com.LMS.repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepo teacherRepo;
    public void createTeacher(Teacher teacher) {
        boolean findEmail = teacherRepo.existsByemail(teacher.getEmail());
        if (findEmail){
            throw new NotFoundException("Email already registered (gehort zu anderem person)");
        }
        teacherRepo.save(teacher);
    }


    public List<Teacher> getallteachers() {
        return teacherRepo.findAll();
    }

    public Teacher findById(Long id) {
        Teacher teacher = teacherRepo.findById(id).orElseThrow(()->new NotFoundException("Entered id not found from: " + id));
        return teacher;
    }

    public void deleteById(Long id) {
        Teacher teacher = findById(id);
        teacherRepo.delete(teacher);
    }

    public void updateTeacher(Long id, TeacherDTO teacherDTO) {
        Teacher teacher = findById(id);

        boolean existemail = teacherRepo.existsByemail(teacherDTO.getEmail());
        boolean belongemail = teacherDTO.getEmail().equals(teacher.getEmail());

        if (existemail && !belongemail){
            throw new Conflikt("Email conflikt");
        }
        teacherRepo.save(saveUpdatedTteacher(teacher, teacherDTO));

    }

    public Teacher saveUpdatedTteacher(Teacher teacher, TeacherDTO teacherDTO){
        teacher.setName(teacherDTO.getName());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setPhoneNumber(teacherDTO.getPhoneNumber());

        return teacher;
    }

    public Page<Teacher> pagebleFind(Pageable teachersPageble) {
        Page<Teacher> result = teacherRepo.findAll(teachersPageble);
        return result;
    }
}
