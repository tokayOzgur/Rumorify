export const Input = (prop) => {
  const { id, label, error, onChange, type, defaultValue } = prop;
  return (
    <div>
      {label && <label htmlFor={id}>{label}</label>}
      <input
        className={error ? "form-control is-invalid" : "form-control mb-3"}
        type={type}
        id={id}
        name={id}
        onChange={onChange}
        defaultValue={defaultValue}
      />
      <div className="invalid-feedback">{error}</div>
    </div>
  );
};
