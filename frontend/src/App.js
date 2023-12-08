import "./App.css";
import React, { useState, setState, useEffect } from "react";
import Header from "./components/header";
import RegistrationForm from "./components/registration";
import registerService from "./services/register";
import CourseForm from "./components/courseRegistration";
import Exit from "./components/exit";
import NavBar from "./components/NavBar";
import { confirmAlert } from "react-confirm-alert";

function App() {
  const [userData, setUserData] = useState(null);
  // const [isSubmit, setIsSubmit] = useState(false);
  const [selectedList, setSelectedList] = useState([]);

  //starts here
  const handleRegister = async (details) => {
    try {
      const userObject = await registerService.register(details);

      if (!userObject) {
        console.log(userObject);
        setUserData(null);
        alertBox("Email id already exists");
        return;
      }
      setUserData(userObject);
      // console.log(userObject)
      window.localStorage.setItem("loggedInUser", JSON.stringify(userObject));
      return userData;
    } catch (exception) {
      // setUserData(null)
      alertBox("Choose correct department ");
    }
  };

  const alertBox = (m) => {
    confirmAlert({
      title: "Confirm to submit",
      message: m,
      buttons: [
        {
          label: "Ok",
          onClick: () => {
            const currList = window.localStorage.getItem("currentList");
            const courseList = window.localStorage.getItem("courseList");

            if (currList !== null) {
              window.localStorage.removeItem("currentList");
              window.localStorage.removeItem("loggedInUser");
              window.localStorage.removeItem("courseList");
            } else if (courseList.length == 2) {
              window.localStorage.removeItem("loggedInUser");
              window.localStorage.removeItem("courseList");
            }
          },
        },
      ],
    });
  };

  const handleSubmit = () => {
    const currList = window.localStorage.getItem("currentList");
    const courseList = window.localStorage.getItem("courseList");
    //setSelectedList(currList)
    console.log(courseList.length);

    if (courseList.length == 2) {
      alertBox("No course available");
      setUserData(null);
    } else if (currList === null) {
      alertBox("You have not selected any course");
      // setIsSubmit(false)
    } else {
      alertBox("Course(s) have been added successfully!");
      // window.localStorage.removeItem('currentList')
      // window.localStorage.removeItem('loggedInUser')
      setUserData(null);
      // setIsSubmit(true)
    }
  };

  // Effect Hook that parses the local storage for 'loggedInUser' and sets the "user" state if a valid match is found
  // This enables user to login automatically without having to type in the credentials. Caching the login if you will.
  useEffect(() => {
    const loggedInUser = window.localStorage.getItem("loggedInUser");
    if (loggedInUser) setUserData(JSON.parse(loggedInUser));
  }, []);
  return (
    <div className="App">
      {userData === null && <Header />}
      {userData === null && <RegistrationForm startRegister={handleRegister} />}

      {userData !== null && <CourseForm userData={userData} />}
      {userData !== null && (
        <div>
          <button onClick={handleSubmit}>SUBMIT</button>
        </div>
      )}
    </div>
  );
}

export default App;
