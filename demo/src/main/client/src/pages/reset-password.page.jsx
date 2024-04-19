import React, {useContext, useState} from 'react'
import AnimationWrapper from '../common/page-animation'
import {toast, Toaster} from 'react-hot-toast'
import InputBox from '../components/input.component'
import {UserContext} from '../App'
import {changePassword, resetPassword} from "../coreApi/CoreApi.js";
import {Link, useNavigate, useParams} from "react-router-dom";

const ResetPassword = () => {

  let passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;
  const{email,token} = useParams()
  const [password, setPassword] = useState('');
  const [retypePassword,setRetypepassword] = useState('');
  const navigate = useNavigate();
  const handleSubmit = async(e) =>{
    e.preventDefault();

    let formData = {
      password,retypePassword, email,token
    }

    console.log(formData)

    if(!passwordRegex.test(password) || !passwordRegex.test(retypePassword)){
      return toast.error('password should be 6 to 20 characters long with a numeric, 1 uppercase and 1 lowercase lettes')
    }
    if(password!== retypePassword)
      return toast.error('Both the passwords should be same');

    e.target.setAttribute("disabled", true)
    let loadingToast = toast.loading('Updating...')
    try{
      console.log(formData)
      const response = await resetPassword({formData});
      e.target.removeAttribute("disabled")
      toast.success("Password Updated Please login to continue")
      setTimeout( ()=> navigate('/signin'),2000)
    }
    catch(error)  {
      e.target.removeAttribute("disabled")
      console.log(error)
      if(error.response &&  error.response.data && error.response.data.data)
        return toast.error(error.response.data.data);
      else
        return toast.error(`There is some error please contact the administrator`);
    }
    finally {
      toast.dismiss(loadingToast);
    }
  }
  return (


        <AnimationWrapper>
          <Toaster/>
          <section className="h-cover flex items-center justify-center">
            <form id="formElement" className="w-[80%] max-w-[400px]">
              <h1 className="text-4xl font-gelasio capitalize text-center mb-24">
               Reset Password
              </h1>

              <InputBox name="password" type="password" placeholder="New password" icon="fi-rr-envelope"
                        className="profile-edit-input"
                        value = {password} setValue={setPassword}
              />
              <InputBox name="retypepassword" type="password" placeholder="Retype new Password" icon="fi-rr-envelope"
                        className="profile-edit-input"
                        value = {retypePassword} setValue={setRetypepassword}
              />

              <button className="btn-dark center mt-14" type="submit" onClick={handleSubmit}>
                Change Password
              </button>

            </form>
          </section>
        </AnimationWrapper>

  )
}

export default ResetPassword;