/*
 * Minesweeper Server
 *
 * This is a Minesweeper server.  You can find out more about it at [github.com/seguijoaquin/minesweeper](github.com/seguijoaquin/minesweeper) 
 *
 * API version: 1.0.0
 * Generated by: Swagger Codegen (https://github.com/swagger-api/swagger-codegen.git)
 */
package swagger
import (
	"time"
)

type Game struct {
	Id int64 `json:"id,omitempty"`
	Rows int32 `json:"rows,omitempty"`
	Cols int32 `json:"cols,omitempty"`
	Mines int32 `json:"mines,omitempty"`
	StartedAt time.Time `json:"started_at,omitempty"`
	Status string `json:"status,omitempty"`
	Board []Cell `json:"board,omitempty"`
}