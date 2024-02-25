import PropTypes from "prop-types";

export const Spinner = (props) => {
  const { size } = props || "sm";
  return (
    <span
      className={`spinner-border spinner-border-${size}`}
      aria-hidden="true"
    ></span>
  );
};

Spinner.propTypes = {
  size: PropTypes.node.isRequired,
};
