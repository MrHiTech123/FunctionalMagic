package runecraft.error;

public enum RunecraftError {
    ForLoopNotRunError,
    NonExistanceError,
    RecipeError,
    SyntaxError,
    TypeError,
    UndefinedVariableError,
    VarNameError;
    
    public static <T> String nameFromClass(Class<T> clazz) {
        String fullName = clazz.getName();
        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }
}
