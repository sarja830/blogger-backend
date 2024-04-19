import { useContext, useRef , useState} from "react"
import AnimationWrapper from "../common/page-animation"
import InputBox from "../components/input.component"
import googleIcon from '../imgs/google.png'
import {Link, Navigate, useNavigate} from 'react-router-dom'
import {Toaster ,toast} from 'react-hot-toast'
import axios from 'axios'
import { storeInSession } from "../common/session"
import { UserContext } from "../App"
import { authWithGoogle } from "../common/firebase"
import {loginSignup, resendVerificationMail} from "../coreApi/CoreApi.js";
// const navigate = useNavigate();
let emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
let passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}$/;

const UserAuthForm = ({type})=>{

    const [fullname, setFullname] = useState('');
    const [password,setPassword] = useState('');
    const [email, setEmail] = useState('');
    let navigate = useNavigate();
    let {userAuth: {authenticationToken}, setUserAuth} = useContext(UserContext)
    const userAuthThroughServer = async (serverRoute,formData,type )=>{
        let loadingToast =  toast.loading("Please wait...");
        try {
            const data = await loginSignup({serverRoute, formData});
            console.log(data)
            toast.dismiss(loadingToast);
            if (type === 'sign-in') {
                console.log(data)
                storeInSession("user", JSON.stringify(data));
                setUserAuth(data)
            } else {
                navigate(`/verify-email/${email}`);
            }
        }
        catch(error) {
            toast.dismiss(loadingToast);
            console.log(error)
            if (error.code == "ERR_NETWORK") {
                toast.error("No internet connection Or backend server down");
            }
            else if(error.response && error.response.status==406) {
                toast.error(error.response.data.message);
                try{
                    await resendVerificationMail({email});
                }
                catch(err){
                    toast.error("Problem in sending verification mail");
                }
                navigate(`/verify-email/${email}`);
            }
            else
            {
                if (error.response)
                    toast.error(error.response.data.data);
                else
                    toast.error("Please contact the administrator");
            }
        }
    }

    const handleSubmit = (e) =>{
        let formData = {
            email:email,
            username:email,
            password:password,
            name:fullname
        }
        e.preventDefault();

        let serverRoute = type === 'sign-in' ? '/auth/signin' : '/auth/signup'
        if(type !== 'sign-in'){
            if(!fullname.length)
                return toast.error( "Please Enter your full name")
        }
        if(!email.length)
            return toast.error( "Enter your email")
        if(!emailRegex.test(email))
            return toast.error( "Email is invalid")
        if(!passwordRegex.test(password))
            return toast.error( 'password should be 6 to 20 characters long with a numeric, 1 uppercase and 1 lowercase lettes')

        userAuthThroughServer(serverRoute, formData,type)
    }

    const handleGoogleAuth = (e) =>{
        e.preventDefault()
        authWithGoogle().then((user)=>{
            let serverRoute = "/google-auth"
            let formData = {
                authenticationToken: user.accessToken
            }
            userAuthThroughServer(serverRoute, formData)
        }).catch((err)=>{
            console.log(err)
            toast.error('Trouble login through Google')
            return
        })
    }
    return (
        authenticationToken ?
            <Navigate to='/'/>
            :
            <AnimationWrapper keyValue={type}>
                <section className="h-cover flex items-center justify-center">
                    <Toaster/>
                    <form id="formElement" className="w-[80%] max-w-[400px]">
                        <h1 className="text-4xl font-gelasio capitalize text-center mb-24">{type === "sign-in" ? "Welcome Back" : "Join us today"}
                        </h1>

                        {
                            type !== "sign-in" ?
                                <InputBox name="fullname" type="text" placeholder="Full Name" icon="fi-rr-user"
                                          value = {fullname} setValue={setFullname}
                                /> : ""
                        }
                        <InputBox name="email" type="email" placeholder="Email" icon="fi-rr-envelope"
                                  value = {email} setValue={setEmail}
                        />
                        <InputBox name="password" type="password" placeholder="Password" icon="fi-rr-key"
                                  value = {password} setValue={setPassword}
                        />

                        <button className="btn-dark center mt-14" type="submit" onClick={handleSubmit}>
                            {type.replace("-", " ")}
                        </button>

                        {/*<div className="relative w-full flex items-center gap-2 my-10 opacity-10 uppercase text-black font-bold">*/}
                        {/*    <hr className="w-1/2 border-black"/>*/}
                        {/*    <p>Or</p>*/}
                        {/*    <hr className="w-1/2 border-black"/>*/}
                        {/*</div>*/}
                        {/*<button onClick={handleGoogleAuth} className="btn-dark flex items-center justify-center gap-4 w-[90%] center">*/}
                        {/*    <img src={googleIcon} alt="googleImg" className="w-4"/>*/}
                        {/*    Continue with google</button>*/}
                        {
                            type === "sign-in"?
                                <>
                                    <p className="mt-6 text-dark-grey text-xl text-center">
                                        Don't have and account ?
                                        <Link to="/signup" className="underline text-black text-xl ml-1">Join us Now</Link>
                                    </p>
                                    <p className="mt-6 text-dark-grey text-xl text-center">
                                        Forgot Password ?
                                        <Link to="/forgot-password" className="underline text-black text-xl ml-1">Email reset password link</Link>
                                    </p>
                                </>
                                :
                                <p className="mt-6 text-dark-grey text-xl text-center">
                                    Already a member ?
                                    <Link to="/signin" className="underline text-black text-xl ml-1">Sign in here</Link>
                                </p>
                        }
                    </form>
                </section>
            </AnimationWrapper>
    )
}

export default UserAuthForm