import React from "react";
import slider1 from "@/assets/slider-1.png";
import slider2 from "@/assets/slider-2.png";
import { useTranslation } from "react-i18next";

export const Introduction = () => {
  const { t } = useTranslation();
  return (
    <div className="container-fluid">
      <div className="container">
        <div className="row">
          <img src={slider1} className="d-block w-100" alt="slider1" />
        </div>
        <div className="row text-center my-3 border rounded shadow-sm">
          <div className="col-12 mt-3">
            <h3>{t("aboutUs")}</h3>
          </div>
          <div className="col-12 mb-3">
            <p>{t("aboutUsText")}</p>
          </div>
        </div>
        <div className="row">
          <img src={slider2} className="d-block w-100" alt="slider2" />
        </div>
        <div className="row text-center my-3  border rounded shadow-sm">
          <div className="col-12 mt-3">
            <h3>{t("ourMission")}</h3>
          </div>
          <div className="col-12 mb-3">
            <p>{t("ourMissionText")}</p>
          </div>
        </div>
      </div>
    </div>
  );
};
