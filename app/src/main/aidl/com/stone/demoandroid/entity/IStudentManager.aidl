// IStudentManager.aidl
package com.stone.demoandroid.entity;

// Declare any non-default types here with import statements
import com.stone.demoandroid.entity.Student;
interface IStudentManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    List<Student> getStudentList();
    void addStudent(in Student student);

}
