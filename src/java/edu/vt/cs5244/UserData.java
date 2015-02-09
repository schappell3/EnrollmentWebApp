/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vt.cs5244;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 * The purpose is to store student information from a registration form.
 * 
 * @author Scott Chappell
 */
public class UserData 
{
    private String stdName;         // Student name
    private String stdPassword;     // Student password
    private String stdRePassword;   // Student re-typed password
    private String stdCity;         // Student city
    private String stdState;        // Student state code
    private String stdZip1;         // First 5 digits of zip code
    private String stdZip2;         // Last 4 digits of zip code
    private String stdPhone1;       // First 3 digits of phone number
    private String stdPhone2;       // Middle 3 digits of phone number
    private String stdPhone3;       // Last 4 digits of phone number
    private String stdSurvey1;      // First survey question answer
    Set<String> stdSurvey2;         // Set of second survey question answers
    List<String> courseHistory;     // History of all courses the student has enrolled in 
    
    /**
     * A constructor that sets all the strings to be empty, and initializes the
     * collections 
     */
    public UserData()
    {
        stdName = "";
        stdPassword = "";
        stdRePassword = "";
        stdCity = "";
        stdState = "";
        stdZip1 = "";
        stdZip2 = "";
        stdPhone1 = "";
        stdPhone2 = "";
        stdPhone3 = "";
        stdSurvey1 = "";
        stdSurvey2 = new HashSet<String>();
        courseHistory = new LinkedList<String>();
    }
    
    /**
     * A method that sets stdName
     * 
     * @param name The student's name 
     */
    public void setStdName(String name)
    {
        stdName = name;
    }
    
    /**
     * A method that sets stdPassword
     * 
     * @param password The student's password 
     */
    public void setStdPassword(String password)
    {
        stdPassword = password;
    }
    
    /**
     * A method that sets stdRePassword
     * 
     * @param rePassword The student's re-typed password 
     */
    public void setStdRePassword(String rePassword)
    {
        stdRePassword = rePassword;
    }
    
    /**
     * A method that sets stdCity
     * 
     * @param city The student's city
     */
    public void setStdCity(String city)
    {
        stdCity = city;
    }
    
    /**
     * A method that sets stdState
     * 
     * @param state The student's state
     */
    public void setStdState(String state)
    {
        stdState = state;
    }
    
    /**
     * A method that sets stdZip1
     * 
     * @param zip1 The student's first 5 digits of the zip code
     */
    public void setStdZip1(String zip1)
    {
        stdZip1 = zip1;
    }
    
    /**
     * A method that sets stdZip2
     * 
     * @param zip2 The student's last 4 digits of the zip code
     */
    public void setStdZip2(String zip2)
    {
        stdZip2 = zip2;
    }
    
    /**
     * A method that sets stdPhone1
     * 
     * @param phone1 The student's first 3 digits of the phone number
     */
    public void setStdPhone1(String phone1)
    {
        stdPhone1 = phone1;
    }
    
    /**
     * A method that sets stdPhone2
     * 
     * @param phone2 The student's middle 3 digits of the phone number
     */
    public void setStdPhone2(String phone2)
    {
        stdPhone2 = phone2;
    }
    
    /**
     * A method that sets stdPhone3
     * 
     * @param phone3 The student's last 4 digits of the phone number
     */
    public void setStdPhone3(String phone3)
    {
        stdPhone3 = phone3;
    }
    
    /**
     * A method that sets stdSurvey1
     * 
     * @param education The student's answer for the education question
     */
    public void setStdSurvey1(String education)
    {
        stdSurvey1 = education;
    }
    
    /**
     * Adds the given survey question answers to the collection
     * 
     * @param advertisement The student's answer for the education question 
     */
    public void setStdSurvey2(String advertisement)
    {
        stdSurvey2.add(advertisement);
    }
    
    /**
     * Adds the given course to the collection 
     * 
     * @param course A course the student has enrolled in
     */
    public void setCourseHistory(String course)
    {
        courseHistory.add(course);
    }
    
    /**
     * A get method that returns the student's name
     * 
     * @return The name of the student
     */
    public String getStdName()
    {
        return stdName;
    }
    
    /**
     * A get method that returns the student's password
     * 
     * @return The password of the student
     */
    public String getStdPassword()
    {
        return stdPassword;
    }
    
    /**
     * A get method that returns the student's re-typed password
     * 
     * @return The re-typed password of the student
     */
    public String getStdRePassword()
    {
        return stdRePassword;
    }
    
    /**
     * A get method that returns the student's city
     * 
     * @return The city of the student
     */
    public String getStdCity()
    {
        return stdCity;
    }
    
    /**
     * A get method that returns the student's state
     * 
     * @return The state of the student
     */
    public String getStdState()
    {
        return stdState;
    }
    
    /**
     * A get method that returns the student's first 5 digits of the zip code
     * 
     * @return The first 5 digits of the zip code of the student
     */
    public String getStdZip1()
    {
        return stdZip1;
    }
    
    /**
     * A get method that returns the student's last 4 digits of the zip code
     * 
     * @return The last 4 digits of the zip code of the student
     */
    public String getStdZip2()
    {
        return stdZip2;
    }
    
    /**
     * A get method that returns the student's first 3 digits of the phone number
     * 
     * @return The first 3 digits of the phone number of the student
     */
    public String getStdPhone1()
    {
        return stdPhone1;
    }
    
    /**
     * A get method that returns the student's middle 3 digits of the phone number
     * 
     * @return The middle 3 digits of the phone number of the student
     */
    public String getStdPhone2()
    {
        return stdPhone2;
    }
       
    /**
     * A get method that returns the student's last 4 digits of the phone number
     * 
     * @return The last 4 digits of the phone number of the student
     */
    public String getStdPhone3()
    {
        return stdPhone3;
    }
    
    /**
     * A get method that returns the student's answer to the education survey question
     * 
     * @return The answer to the eduction survey question of the student
     */
    public String getStdSurvey1()
    {
        return stdSurvey1;
    }
    
    /**
     * A get method that returns a collection of courses that the student has enrolled in
     * 
     * @return A collection of courses of the student
     */
    public List<String> getCourseHistory()
    {
        return courseHistory;
    }
    
}
