/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vt.cs5244;

import java.util.Set;

/**
 * The purpose is to make the HW1_BEM thread safe (with synchronization)
 * 
 * @author Scott Chappell
 */
public class SafeHW1_BEM implements BasicEnrollmentManager
{
    // BasicEnrollmentManager object
    private BasicEnrollmentManager wrappedBEM;
    
    /**
     * A constructor that creates a new HW1_BEM object
     */
    public SafeHW1_BEM() 
    {
      wrappedBEM = new HW1_BEM();
    }
    
    /**
     * Add a new student to the system.
     * Each student is assigned an identifier which is guaranteed to be unique
     * among all student records currently in this instance.
     *
     * @param name Name of this student (cannot be null)
     * @return Unique identifier for the student; implementations may not return null
     * @throws StudentEnrollmentException
     *  if the student name is null
     */
    public synchronized Integer addStudent(String name) throws StudentEnrollmentException 
    {
      return wrappedBEM.addStudent(name);
    }
    
    /**
     * Remove a student from the system.
     * A student must be enrolled in no courses to be removed successfully.
     * After successful removal, the student's identifier may or may not be reassigned
     * to a subsequent new student; details of any such reuse (or lack of reuse)
     * may vary by implementation.
     *
     * @param studentID Unique identifier for the student
     * @return true if the student was successfully removed;
     *  false otherwise (if the student is enrolled in a course)
     * @throws StudentEnrollmentException
     *  if no student with the given ID exists in the system
     */
    public synchronized boolean removeStudent(Integer studentID) throws StudentEnrollmentException
    {
        return wrappedBEM.removeStudent(studentID);
    }
  
    /**
     * Retrieve the name of a student from the unique identifier.
     *
     * @param studentID The unique identifier for the student
     * @return the name of this student
     * @throws StudentEnrollmentException
     *  if no student with the given ID exists in the system
     */
    public synchronized String getStudentName(Integer studentID) throws StudentEnrollmentException
    {
        return wrappedBEM.getStudentName(studentID);
    }

    /**
     * Add a new course to the system.
     * Each new course is associated with an initial capacity and a description.
     *
     * @param name The name of the course (must be unique; cannot be null)
     * @param seats The capacity of this course (cannot be negative)
     * @param description A brief description of the course (may be null)
     * @return true if course was successfully added;
     *  false otherwise (if the specified capacity is negative, regardless of course name)
     * @throws CourseEnrollmentException
     *  if the course name already exists in the system, or is null
     */
    public synchronized boolean addCourse(String name, int seats, String description) throws CourseEnrollmentException
    {
        return wrappedBEM.addCourse(name, seats, description);
    }

    /**
     * Modify the course to increase or decrease the total capacity.
     * Any course may be assigned more or fewer seats.
     * The new capacity is specified as a positive or negative change from the current capacity.
     * If the change is positive, all added seats will become available.
     * If the change is negative, the resulting capacity must be at least
     * sufficient to hold all the students who are already enrolled;
     * in other words, the number of available seats must always remain non-negative.
     *
     * @param name Course name
     * @param seats Number of seats to increase (positive) or decrease (negative)
     * @return true if the adjustment was successful;
     *  false otherwise (if the adjustment would have yielded negative available seats)
     * @throws CourseEnrollmentException
     *  if no course with the given name exists in the system
     */
    public synchronized boolean adjustCourseCapacity(String name, int seats) throws CourseEnrollmentException
    {
        return wrappedBEM.adjustCourseCapacity(name, seats);
    }

    /**
     * Retrieve the current capacity of a course.
     * This is the maximum possible number of seats in the course.
     * It is always equal to the number of seats currently available plus
     * the number of currently enrolled students.
     *
     * @param name Course name
     * @return the current capacity of the course
     * @throws CourseEnrollmentException
     *  if no course with the given name exists in the system
     */
    public synchronized int getCourseCapacity(String name) throws CourseEnrollmentException
    {
        return wrappedBEM.getCourseCapacity(name);
    }

    /**
     * Retrieve the current number of available seats for a course.
     * This number is the capacity, minus the number of
     * students currently enrolled. The number of available seats
     * will always be non-negative, and no greater than the course capacity.
     *
     * @param name Course name
     * @return the current number of seats available
     * @throws CourseEnrollmentException
     *  if no course with the given name exists in the system
     */
    public synchronized int getCourseAvailableSeats(String name) throws CourseEnrollmentException
    {
        return wrappedBEM.getCourseAvailableSeats(name);
    }

    /**
     * Retrieve the description of a course.
     *
     * @param name Course name
     * @return the description of the specified course; the description may be null
     * @throws CourseEnrollmentException
     *  if no course with the given name exists in the system
     */
    public synchronized String getCourseDescription(String name) throws CourseEnrollmentException
    {
        return wrappedBEM.getCourseDescription(name);
    }

    /**
     * Complete a course, optionally removing it from the system.
     * This is used to clear all enrolled students from a course.
     * The students will no longer be enrolled in the course.
     * If the remove parameter is true, the course will then be removed from the system.
     * If the course is not removed, its entire capacity will become available.
     *
     * @param name Course name
     * @param remove Indicates whether to remove the course from the system
     * @throws CourseEnrollmentException
     *  if no course with the given name exists in the system
     */
    public synchronized void completeCourse(String name, boolean remove) throws CourseEnrollmentException
    {
        wrappedBEM.completeCourse(name, remove);
    }

    /**
     * Retrieve all courses in the system.
     * If there are no courses in the system, the returned Set has zero elements.
     * The sequence of elements is unspecified, and may vary by implementation.
     * The returned collection is fixed, and must not reflect any future changes.
     *
     * @return a Set of course names, one course per element; the Set may be empty but never null
     */
    public synchronized Set<String> getAllCourses()
    {
        return wrappedBEM.getAllCourses();
    }

    /**
     * Retrieve all students in the system.
     * If there are no students in the system, the returned Set has zero elements.
     * The sequence of elements is unspecified, and may vary by implementation.
     * The returned collection is fixed, and must not reflect any future changes.
     *
     * @return a Set of student identifiers, one student per element; the Set may be empty but never null
     */
    public synchronized Set<Integer> getAllStudents()
    {
        return wrappedBEM.getAllStudents();
    }

    /**
     * Retrieve all students currently enrolled in a course.
     * If there are no students enrolled in the course, the returned Set has zero elements.
     * The sequence of elements is unspecified, and may vary by implementation.
     * The returned collection is fixed, and must not reflect any future changes.
     *
     * @param name Course name
     * @return a Set of student identifiers, one student per element; the Set may be empty but never null
     * @throws CourseEnrollmentException
     *  if no course with the given name exists in the system
     */
    public synchronized Set<Integer> getStudentsForCourse(String name) throws CourseEnrollmentException
    {
        return wrappedBEM.getStudentsForCourse(name);
    }

    /**
     * Retrieve all courses in which a student is enrolled.
     * If this student  is not enrolled in any courses, the returned set has zero elements.
     * The sequence of elements is unspecified, and may vary by implementation.
     * The returned collection is fixed, and must not reflect any future changes.
     *
     * @param studentID The unique identifier for the student
     * @return a set of course names, one course per element; the Set may be empty but never null
     * @throws StudentEnrollmentException
     *  if no student with the given ID exists in the system
     */
    public synchronized Set<String> getCoursesForStudent(Integer studentID) throws StudentEnrollmentException
    {
        return wrappedBEM.getCoursesForStudent(studentID);
    }

    /**
     * Enroll a student in a course.
     * Reserves a seat for the student in the course.
     * The course must have at least one available seat for this to succeed,
     * and the student must not already be enrolled in the course.
     * A successful enrollment reduces the available seats in the course by one.
     *
     * @param studentID The unique identifier for the student
     * @param name Course name
     * @return true if the student was successfully enrolled;
     *  false otherwise (if the course has no available seats,
     *  or the student is already enrolled in the course)
     * @throws StudentEnrollmentException
     *  if no student with the given ID exists in the system, regardless of course name
     * @throws CourseEnrollmentException
     *  if no course with the given name exists in the system
     */
    public synchronized boolean enrollStudentInCourse(Integer studentID, String name) throws StudentEnrollmentException, CourseEnrollmentException
    {
        return wrappedBEM.enrollStudentInCourse(studentID, name);
    } 
}
