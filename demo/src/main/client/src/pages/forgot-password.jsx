import AnimationWrapper from "../common/page-animation.jsx";
import {toast, Toaster} from "react-hot-toast";
import InputBox from "../components/input.component.jsx";
import {Link} from "react-router-dom";
import {useState} from "react";
import {resendVerificationMail, sendResetPasswordMail} from "../coreApi/CoreApi.js";


const ForgotPassword = () => {
    const [email, setEmail] = useState("");
    const handleSubmit = (e) => {
        e.preventDefault();
        const sendMail = async () => {
            let loadingToast =  toast.loading("Please wait...");
            try{
                await sendResetPasswordMail({email});
                toast.success(
                    (t) => (
                        <span>
                                <br/>
                                 <div>
                                     Please check your email for the reset password link
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
            catch (err){
                console.log(err);
                if(err.response && err.response.data && err.response.data.data)
                    toast.error(err.response.data.data);
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
                <section className="h-cover flex items-center justify-center">
                    <Toaster/>
                    <form id="formElement" className="w-[80%] max-w-[400px]">
                        <h1 className="text-4xl font-gelasio capitalize text-center mb-24">
                            Please enter email
                        </h1>

                        <InputBox name="email" type="email" placeholder="Email" icon="fi-rr-envelope"
                                  value = {email} setValue={setEmail}
                        />

                        <button className="btn-dark center mt-14" type="submit" onClick={handleSubmit}>
                            send reset password link
                        </button>
                        <p className="mt-6 text-dark-grey text-xl text-center">
                            Not Registered yet ?
                            <Link to="/signup" className="underline text-black text-xl ml-1"> Sign up here</Link>
                        </p>
                    </form>
                </section>
            </AnimationWrapper>
        </>
    );
}

export default ForgotPassword;
