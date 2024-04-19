import AnimationWrapper from "../common/page-animation.jsx";

import {Link, useParams} from "react-router-dom";
import React, {useContext} from "react";
import darkFullLogo from "../imgs/full-logo-dark1.png";
import lightFullLogo from "../imgs/full-logo-light1.png";
import {ThemeContext} from "../App.jsx";
import {resendVerificationMail, sendResetPasswordMail} from "../coreApi/CoreApi.js";
import {toast, Toaster} from "react-hot-toast";


const VerifyEmail = () => {

    let {theme} = useContext(ThemeContext);

    let {email:email} = useParams();
    const handleSubmit = (e) => {
        e.preventDefault();
        let loadingToast =  toast.loading("Please wait...");
        const sendMail = async () => {
            try{
                await resendVerificationMail({email});
                toast.success("Reset password link sent to your email");
            }
            catch (err){
                console.log(err);
                if(err.response && err.response.data && err.response.data.data)
                    toast.error(err.response.data.data+`Please login again to get the verification token on your email`);
                else
                    toast.error("There is something wrong with the server");
            }
            finally {
                toast.dismiss(loadingToast);
            }
        }
        sendMail();
    }
    return (
        <>
            <AnimationWrapper>
                <Toaster/>
                <section className='h-cover relative p-10 flex flex-col items-center gap-10 text-center'>

                    <h1 className="text-4xl font-gelasio capitalize text-center mb-24">
                        Check your inbox
                    </h1>
                    <p className='text-dark-grey text-xl leadding-7 -mt-8'> {`We are glad, that you’re with us ? We’ve sent you a verification link to the email address ${email}.`}</p>
                    <p className='text-dark-grey text-xl leadding-7 -mt-8'> {`If you can find the email please check the spam folder of your inbox.`}</p>
                    <p className='text-dark-grey text-xl leadding-7 -mt-8'>If verified please login <Link to="/signin" className='text-black underline'>home page</Link></p>

                    <button className={"btn-dark center mt-14"} type="submit" onClick={ handleSubmit}>
                        Resend Email Verification
                    </button>
                    <div className='mt-auto'>
                        <img src={theme == 'light' ? darkFullLogo : lightFullLogo} alt="Logo" className='h-20 object-contain block mx-auto select-none'/>
                        <p className='mt-5 text-dark-grey'>Read millions of stories around the world</p>
                    </div>
                </section>
            </AnimationWrapper>
        </>
    );
}

export default VerifyEmail;
