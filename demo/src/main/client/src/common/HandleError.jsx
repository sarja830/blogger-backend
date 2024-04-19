import {Navigate, useNavigate} from "react-router-dom";
import {toast} from "react-hot-toast";


export const HandleError =async (err,navigate) =>{
    console.log(err)

    if(err.response.status===401) {
         toast.error("Please login again to continue");
         navigate(`/signin`, {replace: true});
    }
    else if(err.response.status===403) {
        toast.error("You are not authorized to perform this action");
         navigate(`/`, {replace: true});
    }
    else if(err.response.status===500) {
        toast.error("Internal server error");
    }
    else if(err.code==="ERR_NETWORK")
        toast.error("Please check your internet connection");
    else {
        toast.error("Something went wrong");

    }


}