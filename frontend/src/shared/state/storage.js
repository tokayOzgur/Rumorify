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

export const storeToken = (token) => {
  if (token) {
    localStorage.setItem("token", JSON.stringify(token));
  } else {
    localStorage.removeItem("token");
  }
};

export const loadToken = () => {
  const tokenInStorage = localStorage.getItem("token");
  if (!tokenInStorage) return null;
  try {
    return JSON.parse(tokenInStorage);
  } catch (error) {
    return null;
  }
};
