/**
 * CSpecificMaterialEvaluation.java
 * @author Kasimov Ildar
 */
package production_database.evaluators;

import java.util.Calendar;
import java.util.NoSuchElementException;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import production_database.interfaces.IMaterial;
import production_database.interfaces.ISpecification;

/**
 * class CSpecificMaterialEvaluation
 * The class implements Evaluation.
 * It provides a functionality to select objects that correspond to 
 * the specified material name
 * @author Kasimov Ildar
 *
 */
public final class CSpecificMaterialEvaluation implements Evaluation {
    private IMaterial mMaterial;
    
    public CSpecificMaterialEvaluation(IMaterial material) {
	mMaterial = material;
    }
    
    @Override
    public void evaluate(Candidate candidate) {
	ISpecification spec = (ISpecification)candidate.getObject();

	try {
	    candidate.include(spec.GetMaterialAmount(mMaterial) > 0.0f);
	}
	catch (NoSuchElementException excp) {
	    candidate.include(false);
	}
	catch (IllegalArgumentException excp) {
	    candidate.include(false);
	}
    }

}
