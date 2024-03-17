export const Alert = (props) => {
  const { children, styleType } = props;
  return (
    <div className={`alert alert-${styleType || "info"}`}>
      {children}
    </div>
  );
};
