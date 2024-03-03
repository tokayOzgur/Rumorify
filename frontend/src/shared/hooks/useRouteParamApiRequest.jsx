import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export const useRouteParamApiRequest = (param, httpFunction) => {
  const params = useParams();
  const pathParam = params[param];
  const [data, setData] = useState(null);
  const [apiProgress, setApiProgress] = useState(false);
  const [error, setError] = useState();

  useEffect(() => {
    const sendRequest = async () => {
      setApiProgress(true);
      try {
        const response = await httpFunction(pathParam);
        setData(response.data);
      } catch (error) {
        console.log(error);
        setError(error.response.data.message);
      } finally {
        setApiProgress(false);
      }
    };

    sendRequest();
  }, [pathParam]);

  return { data, apiProgress, error };
};
