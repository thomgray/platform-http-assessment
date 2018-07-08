Given(/^the url "([^"]+)" is returning (\d+) statuses with headers:$/) do |url, status, table|
  stub_request(:get, url).to_return(
    body: 'foo',
    status: status.to_i,
    headers: table.rows_hash
  )
end

When(/^I run the command with the following urls$/) do |table|
  urls = table.raw.flatten
  run_under_test urls
end

Then(/^I should get a result containing (\d+) items?$/) do |length|
  expect(@json_output.size).to eq(length.to_i)
end

Then(/^the result should include$/) do |table|
  expect(@json_output).to include(table.rows_hash)
end

def run_under_test(args)
  @raw_output = %x(java -jar #{EXECUTABLE} #{args.join(' ')})
  begin
    @json_output = JSON.parse(@raw_output)
  rescue JSON::ParserError
    @json_output =  nil;
  end
end
