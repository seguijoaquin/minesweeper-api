/*
 * Minesweeper Server
 *
 * This is a Minesweeper server.  You can find out more about it at [github.com/seguijoaquin/minesweeper](github.com/seguijoaquin/minesweeper) 
 *
 * API version: 1.0.0
 * Generated by: Swagger Codegen (https://github.com/swagger-api/swagger-codegen.git)
 */
package swagger

type MoveRequest struct {
	Row int32 `json:"row"`
	Col int32 `json:"col"`
	Action string `json:"action"`
}
