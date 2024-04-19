import React, {useContext, useEffect} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import {removeFromSession} from "./session.jsx";
import {UserContext} from "../App.jsx";

const AuthVerify = () => {
    let location = useLocation();
    const navigate = useNavigate()
    const { userAuth: {expiresAt} , setUserAuth} = useContext(UserContext);
    useEffect(() => {
        if (expiresAt && expiresAt < new Date()/1000) {
            console.log("Session expired");
            removeFromSession("user");
            setUserAuth({access_token: null})
            navigate("/signin");

        }
    }, [location]);
    return ;
};

export default AuthVerify;