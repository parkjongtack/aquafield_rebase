<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
						<tr>
					</tbody>
				</table>
			</div>
			</form>
		</div>
	</div>
</section>
<script type="text/javascript">
function chkAll(me){
	if($(me).hasClass('chkAll')){
		if($(me).prop("checked") == false){
			$(me).closest("form").find(".lst_check.radio input").each(function(i) {
				if($(this).val() == "off"){
					$(this).prop("checked", true).closest("span").addClass('on');
				}else{
					$(this).prop("checked", false).closest("span").removeClass('on');
				}
			});
	    }else{
	    	$(me).closest("form").find(".lst_check.radio input").each(function(i) {
				if($(this).val() == "on"){
					$(this).prop("checked", true).closest("span").addClass('on');
				}else{
					$(this).prop("checked", false).closest("span").removeClass('on');
				}
			});
	    }
	}else{

	}
		
}
</script>