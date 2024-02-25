package com.example.inventory.control.api.resource.operation.move;

import com.example.inventory.control.api.BaseResponseBody;
import com.example.inventory.control.api.resource.operation.acceptance.model.AcceptWithResourcesBodyModel;
import com.example.inventory.control.api.resource.operation.move.model.MoveWithResourcesResponseBodyModel;

/**
 * Ответ на запрос получения перемещения.
 */
public class MoveResponseBody extends BaseResponseBody {

    /**
     * Перемещение.
     */
    private MoveWithResourcesResponseBodyModel move;

    public MoveWithResourcesResponseBodyModel getMove() {
        return move;
    }

    public void setMove(MoveWithResourcesResponseBodyModel move) {
        this.move = move;
    }
}
