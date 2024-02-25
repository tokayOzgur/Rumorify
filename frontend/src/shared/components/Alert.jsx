import PropTypes from "prop-types";

export const Alert = (props) => {
  const { children, styleType } = props;
  return <div className={`alert alert-${styleType || "info"}`}>{children}</div>;
};

Alert.propTypes = {
  children: PropTypes.node.isRequired,
  styleType: PropTypes.string,
};
