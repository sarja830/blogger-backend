import React, { useContext, useEffect, useRef, useState } from 'react'
import {NavLink, Navigate, Outlet} from 'react-router-dom'
import {UserContext} from '../App'


const SideNav = () => {
    let {userAuth: {authenticationToken,role, new_notification_available}} = useContext(UserContext)

    let page = location.pathname.split('/')[2]

    let [pageState, setPageState] = useState(page.replace('-', ' '))
    let [showSideNav, setShowSideNav] = useState(false)

    let activeTabLine = useRef()
    let sideBarIconTab = useRef()
    let pageStateTap = useRef();

    const chagnePageState = (e) =>{
        let {offsetWidth, offsetLeft} = e.target;
        console.log(offsetLeft, offsetWidth)
        activeTabLine.current.style.width = offsetWidth + 'px'
        activeTabLine.current.style.left = offsetLeft + 'px'

        if(e.target == sideBarIconTab.current){
            setShowSideNav(true)
        }
        else{
            setShowSideNav(false)
        }
    }

    useEffect(() => {
        setShowSideNav(false)
        pageStateTap.current.click()
    }, [pageState])
  return (
    authenticationToken === null ? <Navigate to="/signin" /> :
   <>
    <section className='relative flex gap-10 py-0 m-0 max-md:flex-col'>

        <div className="skicky top-[80px] z-30">

            <div className='relative md:hidden bg-white py-1 border-b border-grey flex flex-nowrap overflow-x-auto '>
                <button ref={sideBarIconTab} className='p-5 capitalize ' onClick={chagnePageState}>
                    <i className='fi fi-rr-bars-staggered pointer-events-none'></i>
                </button>
                <button ref={pageStateTap} className='p-5 capitalize ' onClick={chagnePageState}>
                    {pageState}
                </button>

                <hr ref={activeTabLine} className='absolute bottom-0 duration-500 '/>

            </div>

            <div className={'min-w-[200px] h-[calc(100vh-80px-64px)] md:h-cover md:sticky top-24 overflow-y-auto p-6 md:pr-0 md:border-grey md:border-r absolute max-md:top-[64px] bg-white max-md:w-[calc(100%+80px)] max-md:px-16 max-md:-ml-7 duration-500 ' + (!showSideNav ? 'max-md:opacity-0 max-md:pointer-events-none' : 'opacity-100 pointer-events-auto')}>
                <h1 className='text-xl text-dark-grey mb-3'>Dashboard</h1>
                <hr className='border-grey -ml-6 mb-8 mr-6'/>

                {role !== "USER"?
                    <>
                    < NavLink to={'/dashboard/blogs'} onClick={(e) => setPageState(e.target.innerText)} className='sidebar-link'>
                    <i className='fi fi-rr-document'></i>
                    Blogs
                    </NavLink>

                    <NavLink to={'/editor'} onClick={(e) => setPageState(e.target.innerText)} className='sidebar-link'>
                        <i className='fi fi-rr-file-edit'></i>
                        Write
                    </NavLink>
                    </>: null}

                <NavLink to={'/dashboard/notifications'} onClick={(e) => setPageState(e.target.innerText)} className='sidebar-link'>

                    <div className='relative'>
                        <i className='fi fi-rr-bell'></i>
                        {
                            new_notification_available && <span className='bg-red h-2 w-2 rounded-full absolute z-10 top-0 right-0'></span>
                        }

                    </div>
                        Notification
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                          stroke-width="1.5" stroke="currentColor" className="w-6 h-6">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M11.42 15.17 17.25 21A2.652 2.652 0 0 0 21 17.25l-5.877-5.877M11.42 15.17l2.496-3.03c.317-.384.74-.626 1.208-.766M11.42 15.17l-4.655 5.653a2.548 2.548 0 1 1-3.586-3.586l6.837-5.63m5.108-.233c.55-.164 1.163-.188 1.743-.14a4.5 4.5 0 0 0 4.486-6.336l-3.276 3.277a3.004 3.004 0 0 1-2.25-2.25l3.276-3.276a4.5 4.5 0 0 0-6.336 4.486c.091 1.076-.071 2.264-.904 2.95l-.102.085m-1.745 1.437L5.909 7.5H4.5L2.25 3.75l1.5-1.5L7.5 4.5v1.409l4.26 4.26m-1.745 1.437 1.745-1.437m6.615 8.206L15.75 15.75M4.867 19.125h.008v.008h-.008v-.008Z"/>
                </svg>

                </NavLink>


                <h1 className='text-xl text-dark-grey mb-3 mt-20'>Setting</h1>
                <hr className='border-grey ml-6 mb-8 mr-6'/>
                
                <NavLink to={'/settings/edit-profile'} onClick={(e) => setPageState(e.target.innerText)} className='sidebar-link'>
                    <i className='fi fi-rr-user'></i>
                    Edit Profile
                </NavLink>

                <NavLink to={'/settings/change-password'} onClick={(e) => setPageState(e.target.innerText)} className='sidebar-link'>
                    <i className='fi fi-rr-lock'></i>
                    Change Password
                </NavLink>

            </div>
            
        </div>
        <div className='max-md:-mt-8 mt-5 w-full'>
            <Outlet/>
        </div>
    </section>
   </>
  )
}

export default SideNav