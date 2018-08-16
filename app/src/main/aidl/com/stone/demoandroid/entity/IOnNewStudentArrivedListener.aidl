// IOnNewStudentArrivedListener.aidl
package com.stone.demoandroid.entity;
import com.stone.demoandroid.entity.Student;
// Declare any non-default types here with import statements

interface IOnNewStudentArrivedListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onNewStudentArrived(in Student newStudent);
}
