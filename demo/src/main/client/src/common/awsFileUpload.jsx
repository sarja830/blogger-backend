// export const uploadImage = async(image) =>{
//     try{
//         const formData  = new FormData()
//         formData.append("image", image)
//         const res = await fetch(`${import.meta.env.VITE_SERVER_DOMAIN}/upload-image`, {
//             method: 'POST',
//             body: formData
//         })
//         const data = await res.json();
//         return data.url
//     }
//     catch(err){
//         return err
//     }
// }
import axios from "axios";

export const uploadImage = async(image,authenticationToken) =>{

        const formData  = new FormData()
        formData.append("file", image)
        const res = await axios.post(`${import.meta.env.VITE_SERVER_DOMAIN}/images`, formData,{
            headers: {
                'Authorization': `Bearer ${authenticationToken}`
            }
        })
        // const data = await res.json();
        console.log(res);
        return res.data;
}