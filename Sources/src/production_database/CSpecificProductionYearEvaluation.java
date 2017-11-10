/**
 * CSpecificProductionYearEvaluation.java
 * @author Kasimov Ildar
 */
package production_database;

import java.util.Calendar;

import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import production_database.interfaces.ISpecification;

/**
 * class CSpecificProductionYearEvaluation
 * The class implements Evaluation, that's part of a SODA Evaluation API
 * The main task of the class to select all DB entities that satisfy 
 * the specified year of production
 * @author Kasimov Ildar
 * 
 */
public final class CSpecificProductionYearEvaluation implements Evaluation {
    private int    mYear;
    
    private boolean mExceptSpecifiedYear;
    
    /**
     * The constructor of the class
     * @param year A year of production
     * @param exceptSpecifiedYear true means that all objects except ones which match to specified year will be
     * selected. In other case a query's result will contain only the objects which correspond to the specified year.
     */
    public CSpecificProductionYearEvaluation(int year, boolean exceptSpecifiedYear) {
	mYear = year;
	
	mExceptSpecifiedYear = exceptSpecifiedYear;
    }

    public void evaluate(Candidate candidate) {
	ISpecification spec = (ISpecification)candidate.getObject();

	Calendar calendar = Calendar.getInstance();
	calendar.setTime(spec.GetYearOfProduction());
	
	int productionYear = calendar.get(Calendar.YEAR);

	candidate.include(mExceptSpecifiedYear ? (mYear != productionYear) : (mYear == productionYear));
    }
}
