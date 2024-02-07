export const Input = (prop) => {
  const { id, label, error, onChange, type } = prop;
  return (
    <div className="">
      <label htmlFor={id}>{label}</label>
      <input
        className={error ? "form-control is-invalid" : "form-control mb-3"}
        type={type}
        id={id}
        name={id}
        onChange={onChange}
      />
      <div className="invalid-feedback">{error}</div>
    </div>
  );
};
