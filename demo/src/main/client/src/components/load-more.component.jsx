import React from 'react'

const LoadMoreDataBtn = ({state, fetchDataFunction, additionalParam}) => {
    console.log(state)
    if(state !== null && state.totalDocs > state.results.length){

        return <button
            onClick={() => fetchDataFunction({...additionalParam, page: state.page + 1})}
            className='text-dark-grey p-2 px-3 hover:bg-grey/30 rounded-md flex items-center gap-2'
        >
            Load More
        </button>
    }
}

export default LoadMoreDataBtn