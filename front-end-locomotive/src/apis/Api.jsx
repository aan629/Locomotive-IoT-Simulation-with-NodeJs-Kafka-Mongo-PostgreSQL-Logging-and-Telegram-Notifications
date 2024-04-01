import axios from "axios";

const API_BASE_URL = "http://localhost:9090/";

/*------------------------------------ Coba Pakai Method Fetch ---------------------------------*/
export const fetchingSummarylatest = async () => {
    const URL = `${API_BASE_URL}summary/latest`
    const response = await fetch(URL)
    if (!response.ok) {
      throw new Error("Fetching Error") 
    }
    return await response.json(); 
}

export const fetchingSummaryAll = async (orderBy, page) => {
   const URL = `${API_BASE_URL}summary/all?orderBy=${orderBy}&page=${page}`

   const response = await fetch(URL)
   if (!response.ok) {
      throw new Error("Fetching Error") 
   }
   return await response.json() 
}  


/*------------------------------------ Coba Pakai Method Axios ---------------------------------*/
export const getStatusSummary = async () => {
   try {
       const url = `${API_BASE_URL}summary/allstatus`;
       const response = await axios.get(url);
       const responseData = response.data;

       return responseData;
   } catch (error) {
       const errorData = {
           status: error.response ? error.response.status : null,
           data: error.response ? error.response.data : null,
           message: error.message,
       };

       return errorData;
   }
};