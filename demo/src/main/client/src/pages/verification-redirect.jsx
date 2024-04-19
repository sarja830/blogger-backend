import AnimationWrapper from "../common/page-animation.jsx";

import {Link, useNavigate,Navigate, useParams} from "react-router-dom";
import React, {useContext, useEffect, useState} from "react";
import darkFullLogo from "../imgs/full-logo-dark1.png";
import lightFullLogo from "../imgs/full-logo-light1.png";
import {ThemeContext} from "../App.jsx";
import {toast, Toaster} from "react-hot-toast";
import Loader from "../components/loader.component.jsx";
import {verifyAccount} from "../coreApi/CoreApi.js";
import ForgotPassword from "./forgot-password.jsx";


const verificationRedirect = () => {
    const theme = useContext(ThemeContext);
    const navigate = useNavigate();
    const {token} = useParams();
    const [loading,setLoading] = useState(true);
    const [verified,setVerified] = useState(false);
    useEffect(() => {

        const verifyAccountfunc = async ()=>{
            try{
                await verifyAccount({token});
                setVerified(true);
            }
            catch (err){
                console.log(err);
                if(err.response && err.response.data && err.response.data.data)
                {
                    toast.error(
                        (t) => (
                            <span>
                                <br/>
                                 <div>
                                     {err.response.data.data + ` Please login again to get the verification token on your email`}
                                       <br/>
                                 </div>
                                <br/>
                                <button onClick={() => toast.dismiss(t.id)}
                                        className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-full">
                                     Dismiss
                                 </button>
                            </span>
                        ), {duration: 10000});

                }
                else
                    toast.error("There is something wrong with the server");

                setVerified(false);
            }
            finally {
                setLoading(false);
                setTimeout(() => {
                    navigate('/signin');
                }, 4000)
            }
        };

        verifyAccountfunc();


    }, []);

    return (
        <>
            <Toaster/>
            <AnimationWrapper>

                {loading ?
                    <Loader/>
                    :
                    verified?
                        <section className='h-cover relative p-10 flex flex-col items-center gap-20 text-center'>
                            <div className="bg-white p-6  md:mx-auto">
                                <svg viewBox="0 0 24 24" className="text-green-600 w-16 h-16 mx-auto my-6">
                                    <path fill="currentColor"
                                          d="M12,0A12,12,0,1,0,24,12,12.014,12.014,0,0,0,12,0Zm6.927,8.2-6.845,9.289a1.011,1.011,0,0,1-1.43.188L5.764,13.769a1,1,0,1,1,1.25-1.562l4.076,3.261,6.227-8.451A1,1,0,1,1,18.927,8.2Z">
                                    </path>
                                </svg>
                                <div className="text-center">
                                    <h3 className="md:text-2xl text-base text-gray-900 font-semibold text-center">Account verified successfully!</h3>
                                    <p className="text-gray-600 my-2">Thank you for completing your account verification. You will be redirected to signin shortly.</p>
                                    <p> Have a great day! </p>
                                    <div className="py-10 text-center">

                                        <Link className='btn-dark py-2 ' to='/signin'>
                                            Sign In
                                        </Link>

                                    </div>
                                </div>
                                <div className='mt-auto'>
                                    <img src={theme == 'light' ? darkFullLogo : lightFullLogo} alt="Logo" className='h-20 object-contain block mx-auto select-none'/>
                                    <p className='mt-5 text-dark-grey'>Read millions of stories around the world</p>
                                </div>


                            </div>
                        </section>
                        :
                        <></>


                }
            </AnimationWrapper>
        </>
    );
}

export default verificationRedirect;
