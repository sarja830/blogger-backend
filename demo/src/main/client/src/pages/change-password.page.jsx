import React, {useContext, useState} from 'react'
import AnimationWrapper from '../common/page-animation'
import {toast, Toaster} from 'react-hot-toast'
import InputBox from '../components/input.component'
import {UserContext} from '../App'
import {changePassword} from "../coreApi/CoreApi.js";

const ChangePassword = () => {

  let {userAuth: {authenticationToken,username}} = useContext(UserContext);
  let passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;

  const [oldPassword, setOldPassword] = useState('');
  const [newPassword,setNewPassword] = useState('');


  const handleSubmit = async(e) =>{
    e.preventDefault();

    let formData = {
      oldPassword,newPassword
    }
    console.log(formData)
    if(oldPassword===newPassword)
      return toast.error("Old Password and new Password should not be same")

    if(!oldPassword.length || !newPassword.length)
      return toast.error("Fill all th inputs")


    if(!passwordRegex.test(oldPassword) || !passwordRegex.test(newPassword)){
      return toast.error('password should be 6 to 20 characters long with a numeric, 1 uppercase and 1 lowercase lettes')
    }

    e.target.setAttribute("disabled", true)
    let loadingToast = toast.loading('Updating...')
    try{
      console.log(formData)
      const response = await changePassword({formData}, username);
      toast.dismiss(loadingToast);
      e.target.removeAttribute("disabled")
      return toast.success("Password Updated")
    }
    catch({response})  {
      console.log(response.data.data)
      toast.dismiss(loadingToast);
      e.target.removeAttribute("disabled")
      if(response.data && response.data.data)
        return toast.error(response.data.data);
      else
        return toast.error(`There is some error please contact the administrator`);
    };
  }
  return (
      <AnimationWrapper>
        <Toaster/>
        <form id="formElement">
          <h1 className='max-md:hidden'>Change Password</h1>
          <div className='py-10 w-full md:max-w-[400px]'>
            <InputBox
                name='oldPassword'
                value={oldPassword}
                setValue={setOldPassword}
                type='password'
                className='profile-edit-input' placeholder='Current Password' icon='fi-rr-unlock'/>

            <InputBox
                value={newPassword}
                setValue={setNewPassword}
                name='newPassword' type='password' className='profile-edit-input' placeholder='New Password' icon='fi-rr-unlock'/>

            <button onClick={handleSubmit} className='btn-dark px-10' type='submit'>Change Password</button>
          </div>
        </form>
      </AnimationWrapper>
  )
}

export default ChangePassword