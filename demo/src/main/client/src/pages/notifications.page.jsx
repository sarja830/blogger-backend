import { useContext, useEffect, useState } from "react"
import axios from 'axios'
import { UserContext } from "../App"
import { filterPaginationData } from "../common/filter-pagination-data"
import Loader from "../components/loader.component"
import AnimationWrapper from "../common/page-animation"
import NoDataMessage from "../components/nodata.component"
import NotificationCard from "../components/notification-card.component"
import LoadMoreDataBtn from "../components/load-more.component"
const Notification = () => {
    let {userAuth, setUserAuth, userAuth: {authenticationToken, new_notification_available}} = useContext(UserContext)
    const [filter, setFilter] = useState('all')
    const filters = ['all', 'like', 'comment', 'reply']

    const [notifications, setNotifications] = useState(null)

    const fetchNotifications = ({page, deletedDocCount = 0}) => {
        axios.post(import.meta.env.VITE_SERVER_DOMAIN + '/notifications', {
            page, filter, deletedDocCount
        }, {
            headers: {
                'Authorization': `Bearer ${authenticationToken}`
            }
        })
        .then( async({data: {notifications: data}}) => {

            if(new_notification_available){
                setUserAuth({...userAuth, new_notification_available: false})
            }
            let formatedData = await filterPaginationData({
                state: notifications,
                data,
                page,
                countRoute: '/all-notifications-count',
                data_to_send: {filter},
                user: authenticationToken
            })

            setNotifications(formatedData)

        })
        .catch(err => {
            console.log(err)
        })
    }
    const handleFilter = (e) => {
        let btn = e.target;
        setFilter(btn.innerHTML);
        setNotifications(null)
    }

    useEffect(() => {
        if(authenticationToken){
            fetchNotifications({page: 1})
        }
    }, [authenticationToken, filter])
  return (
      <div className="flex items-center justify-center ">
          <h2 className=" text-4xl tracking-tight leading-10 font-extrabold text-gray-900 sm:text-5xl sm:leading-none md:text-6xl">
              Coming Soon
          </h2>
      </div>

  )
}

export default Notification
{/*<div>*/}
{/*    <h1 className="max-md:hidden">Recent Notification</h1>*/}
{/*    <div className="my-8 flex gap-6 ">*/}
{/*        {*/}
{/*            filters.map((filterName, i) => {*/}
{/*                return <button key={i} className={"py-2 " + (filter == filterName ? "btn-dark" : "btn-light") } onClick={handleFilter}>{filterName}</button>*/}
{/*            })*/}
{/*        }*/}
{/*    </div>*/}

{/*    {*/}
{/*        notifications == null ? <Loader/> : */}
{/*        <>*/}
{/*            {*/}
{/*                notifications.results.length ? */}
{/*                    notifications.results.map((notification, i ) => {*/}
{/*                        return <AnimationWrapper*/}
{/*                            key={i} transition={{delay: i*0.08}}*/}
{/*                        >*/}
{/*                            <NotificationCard data={notification} index={i} notificationState={{notifications, setNotifications}}/>*/}
{/*                        </AnimationWrapper>*/}
{/*                    })*/}
{/*                    :*/}
{/*                    <NoDataMessage message='Nothing Available' />*/}
{/*            }*/}
{/*            <LoadMoreDataBtn state={notifications} fetchDataFunction={fetchNotifications} additionalParam = {{deletedDocCount: notifications.deletedDocCount}}/>*/}
{/*        </>*/}
{/*    }*/}
{/*</div>*/}