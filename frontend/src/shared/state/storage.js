export const storeAuthState = (state) => {
  localStorage.setItem("auth", JSON.stringify(state));
};

export const loadAuthState = () => {
  const defaultState = { id: 0 };
  const authStateInStorage = localStorage.getItem("auth");
  if (!authStateInStorage) return defaultState;
  try {
    return JSON.parse(authStateInStorage);
  } catch (error) {
    return defaultState;
  }
};
