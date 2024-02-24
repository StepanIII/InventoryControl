package com.example.inventory.control.api.resource.operation.move;


import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptResponseBodyModel;
import com.example.inventory.control.api.resource.operation.move.model.MoveResponseBodyModel;

import java.util.List;

/**
 * Тело ответа "Перемещения".
 */
public class AllMoveResponseBody extends BaseResponseBody {

    /**
     * Приемки.
     */
    private List<MoveResponseBodyModel> moves;

    public List<MoveResponseBodyModel> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveResponseBodyModel> moves) {
        this.moves = moves;
    }
}
